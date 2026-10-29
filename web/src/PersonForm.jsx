import { useState } from 'react';
import { addPerson } from './api.js';

const EMPTY = { firstName: '', lastName: '', nickname: '', birthday: '' };

export default function PersonForm({ onAdded }) {
  const [person, setPerson] = useState(EMPTY);
  const [isPending, setIsPending] = useState(false);
  const [error, setError] = useState(null);

  const update = (field) => (e) => setPerson({ ...person, [field]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsPending(true);
    setError(null);
    try {
      await addPerson(person);
      setPerson(EMPTY);
      onAdded();
    } catch (err) {
      setError(err.message);
    } finally {
      setIsPending(false);
    }
  };

  const today = new Date().toISOString().slice(0, 10);

  return (
    <div className="create">
      <h2>New Submission</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="firstName">First Name</label>
        <input id="firstName" type="text" required maxLength={250} value={person.firstName} onChange={update('firstName')} />
        <label htmlFor="lastName">Last Name</label>
        <input id="lastName" type="text" required maxLength={250} value={person.lastName} onChange={update('lastName')} />
        <label htmlFor="nickname">Nickname</label>
        <input id="nickname" type="text" required maxLength={250} value={person.nickname} onChange={update('nickname')} />
        <label htmlFor="birthday">Birthday</label>
        <input id="birthday" type="date" required max={today} value={person.birthday} onChange={update('birthday')} />
        {error && <p role="alert" className="error">{error}</p>}
        <button disabled={isPending}>{isPending ? 'Adding person…' : 'Submit'}</button>
      </form>
    </div>
  );
}
