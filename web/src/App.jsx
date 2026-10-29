import { useCallback, useEffect, useState } from 'react';
import banner from './balloons.jpeg';
import './App.css';
import { getPeople } from './api.js';
import PeopleList from './PeopleList.jsx';
import PersonForm from './PersonForm.jsx';

export default function App() {
  const [people, setPeople] = useState(null);
  const [error, setError] = useState(null);

  const loadPeople = useCallback(() => {
    getPeople()
      .then((data) => {
        setPeople(data);
        setError(null);
      })
      .catch((e) => setError(e.message));
  }, []);

  useEffect(loadPeople, [loadPeople]);

  return (
    <div className="App">
      <header className="App-header">
        <div className="container">
          <img src={banner} className="Birthday-logo" alt="" />
          <h1 className="centered">Nickname and Birthday Database</h1>
        </div>
      </header>
      <main>
        {error && <p role="alert" className="error">Couldn't load birthdays: {error}</p>}
        <PeopleList people={people} />
        <PersonForm onAdded={loadPeople} />
      </main>
    </div>
  );
}
