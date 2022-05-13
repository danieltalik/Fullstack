import {useState} from 'react';

const Form = () => {
    const[firstName, setFirstName] = useState('');
    const[lastName, setLastName] = useState('');
    const[nickName, setNickname] = useState('');
    const[birthday, setBirthday] = useState('');
    const[isPending, setIsPending] = useState(false);

    const handleSubmit= (e) => {
        const person = {firstName, lastName, nickName, birthday};
        setIsPending(true);
        fetch('http://localhost:8080/addNickname',{
            method: 'POST',
            headers: {"Content-Type" : "application/json"},
            body: JSON.stringify(person)
        })
        fetch('http://localhost:8080/addBirthday',{
                    method: 'POST',
                    headers: {"Content-Type" : "application/json"},
                    body: JSON.stringify(person)
                }).then(()=> {
            console.log('New Person Added');
            setIsPending(false);
         }
        )
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
