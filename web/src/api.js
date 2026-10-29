// Relative URLs: Vite proxies /api in dev, and Spring Boot serves both in production.
const BASE = '/api/people';

async function parse(response) {
  if (response.ok) {
    return response.json();
  }
  let message = `Request failed (${response.status})`;
  try {
    const problem = await response.json();
    message = problem.detail || message;
  } catch {
    // body wasn't JSON; keep the generic message
  }
  throw new Error(message);
}

export function getPeople() {
  return fetch(BASE).then(parse);
}

export function addPerson(person) {
  return fetch(BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(person),
  }).then(parse);
}
