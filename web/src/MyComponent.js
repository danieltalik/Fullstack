import React, { Component } from 'react';
import ReactDOM from 'react-dom';

class MyComponent extends Component {

    constructor(){
        super();
        this.state = {
            jsonReturn: null
        }
    }

    componentDidMount(){
        const apiUrl = 'http://localhost:8080/getAllPeople';
        fetch(apiUrl)
            .then((response) => response.json())
            .then(json => {
                this.setState({jsonReturn: json});
                })
            .catch(error => alert(error.message));
    }

    showBirthdays(){
        const peopleArray = this.state.jsonReturn;
        if(peopleArray != null){
            return(
                <div>
                    {
                        peopleArray.map(Person => (
                            <div>
                                <div>
                                    <span>{Person.nickname} </span>
                                    <span>{Person.firstName} </span>
                                    <span>{Person.lastName}</span>
                                    <span> is {Person.age} years old and </span>
                                    <span> was born on {Person.birthday}</span>
                                </div>
                            </div>
                        ))
                    }
                </div>
            )
        }
        return (
            <div>
                No Birthdays for now
            </div>
        )
    }

    render(){
        const birthday = this.showBirthdays();
        return(
            <div>
                <div>{birthday}</div>
            </div>
        );
    }
}
export default MyComponent;