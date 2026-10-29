export default function PeopleList({ people }) {
  if (people === null) {
    return <p>Loading…</p>;
  }
  if (people.length === 0) {
    return <p>No birthdays for now</p>;
  }
  return (
    <ul className="people">
      {people.map((person) => (
        <li key={person.id}>
          <strong>{person.nickname}</strong> {person.firstName} {person.lastName} is {person.age} years
          old and was born on {person.birthday}
        </li>
      ))}
    </ul>
  );
}
