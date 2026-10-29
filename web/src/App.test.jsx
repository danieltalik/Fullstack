import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { describe, expect, it, vi } from 'vitest';
import App from './App.jsx';

const dino = { id: 1, firstName: 'Daniel', lastName: 'Talik', nickname: 'Dino', birthday: '1988-09-20', age: 38 };
const ada = { id: 2, firstName: 'Ada', lastName: 'Lovelace', nickname: 'Countess', birthday: '1815-12-10', age: 210 };

function jsonResponse(body, status = 200) {
  return Promise.resolve(new Response(JSON.stringify(body), {
    status,
    headers: { 'Content-Type': 'application/json' },
  }));
}

describe('App', () => {
  it('shows people from the API', async () => {
    vi.spyOn(globalThis, 'fetch').mockImplementation(() => jsonResponse([dino]));
    render(<App />);
    expect(await screen.findByText('Dino')).toBeInTheDocument();
    expect(screen.getByText(/is 38 years old/)).toBeInTheDocument();
  });

  it('shows an empty message', async () => {
    vi.spyOn(globalThis, 'fetch').mockImplementation(() => jsonResponse([]));
    render(<App />);
    expect(await screen.findByText('No birthdays for now')).toBeInTheDocument();
  });

  it('shows an error when loading fails', async () => {
    vi.spyOn(globalThis, 'fetch').mockImplementation(() => jsonResponse({ detail: 'boom' }, 500));
    render(<App />);
    expect(await screen.findByRole('alert')).toHaveTextContent('boom');
  });

  it('adds a person and refreshes the list without reloading the page', async () => {
    let people = [dino];
    const fetchMock = vi.spyOn(globalThis, 'fetch').mockImplementation((url, options) => {
      if (options?.method === 'POST') {
        people = [dino, ada];
        return jsonResponse(ada, 201);
      }
      return jsonResponse(people);
    });
    const user = userEvent.setup();
    render(<App />);
    await screen.findByText('Dino');

    await user.type(screen.getByLabelText('First Name'), 'Ada');
    await user.type(screen.getByLabelText('Last Name'), 'Lovelace');
    await user.type(screen.getByLabelText('Nickname'), 'Countess');
    await user.type(screen.getByLabelText('Birthday'), '1815-12-10');
    await user.click(screen.getByRole('button', { name: 'Submit' }));

    expect(await screen.findByText('Countess')).toBeInTheDocument();
    const post = fetchMock.mock.calls.find(([, options]) => options?.method === 'POST');
    expect(JSON.parse(post[1].body)).toEqual({
      firstName: 'Ada', lastName: 'Lovelace', nickname: 'Countess', birthday: '1815-12-10',
    });
    expect(screen.getByLabelText('First Name')).toHaveValue('');
  });

  it('keeps the form usable after a failed save', async () => {
    vi.spyOn(globalThis, 'fetch').mockImplementation((url, options) =>
      options?.method === 'POST' ? jsonResponse({ detail: 'Invalid request content.' }, 400) : jsonResponse([]));
    const user = userEvent.setup();
    render(<App />);
    await screen.findByText('No birthdays for now');

    await user.type(screen.getByLabelText('First Name'), 'A');
    await user.type(screen.getByLabelText('Last Name'), 'B');
    await user.type(screen.getByLabelText('Nickname'), 'C');
    await user.type(screen.getByLabelText('Birthday'), '2000-01-01');
    await user.click(screen.getByRole('button', { name: 'Submit' }));

    expect(await screen.findByText('Invalid request content.')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'Submit' })).toBeEnabled();
  });
});
