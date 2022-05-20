import {useState} from 'react';

const Form = () => {
    const[firstName, setFirstName] = useState('');
    const[lastName, setLastName] = useState('');
    const[nickName, setNickname] = useState('');
    const[birthday, setBirthday] = useState('');
    const[isPending, setIsPending] = useState(false);

    const handleSubmit= (e) => {
        e.preventDefault();
        const person = {firstName, lastName, nickName, birthday};
        setIsPending(true);
        fetch('http://localhost:8080/submitNewPerson',{
                   method: 'POST', // or 'PUT'
                     headers: {
                       'Content-Type': 'application/json',
                     },
                     body: JSON.stringify(person),
                })
                .then(response => response.json())
                .then(data => {
                  console.log('Success:', data);
                  setIsPending(false);
                }).catch((error) => {
                    console.error('Error:', error);
                  });
                  window.location.reload();

    }

        return(
            <div className="create">
                <h2>New Submission</h2>
                <form onSubmit={handleSubmit}>
                    <label>First Name</label>
                    <input type="text" required value={firstName} onChange={(e) => setFirstName(e.target.value)}/>
                    <label>Last Name</label>
                    <input type="text" required value={lastName} onChange={(e) => setLastName(e.target.value)}/>
                    <label>Nickname</label>
                    <input type="text" required value={nickName} onChange={(e) => setNickname(e.target.value)}/>
                    <label>Birthday</label>
                    <input type="date" required value={birthday} onChange={(e) => setBirthday(e.target.value)}/>
                    {!isPending && <button>Submit</button>}
                    {isPending && <button disabled>Adding person...</button>}
                </form>
            </div>
        )
}
export default Form
