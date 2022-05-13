import {useState} from 'react';

const String = () => {
    const[firstName, setFirstName] = useState('');
    const[lastName, setLastName] = useState('');
    const[nickName, setNickname] = useState('');
    const[birthday, setBirthday] = useState('');
    const[isPending, setIsPending] = useState(false);

    const handleSubmit= (e) => {
        const person = {firstName, lastName, nickName, birthday};
        setIsPending(true);
        fetch('http://localhost:8080/person?nickname=Dino',{
            method: 'GET',
            headers: {"Content-Type" : "application/json"},
            body: JSON.stringify(person)
        })
    }

        return(
            <div className="create">
                <h2>New Submission</h2>
                <form onSubmit={handleSubmit}>
                    <label>Nickname</label>
                    <input type="text" required value={nickName} onChange={(e) => setNickname(e.target.value)}/>
                    {!isPending && <button>Submit</button>}
                    {isPending && <button disabled>Adding person...</button>}
                </form>
            </div>
        )
}
export default String
