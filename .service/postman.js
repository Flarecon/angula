const user = "user";
const pass = "1234";
const url = "http://127.0.0.1:8080/cache/task/1";
const token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbG9rZzI1MiIsImlhdCI6MTc1MDkwNzQzOCwiZXhwIjoxNzUwOTM2MjM4fQ.L-Y6N6sXHQspdH1OJAlIaMJ5Zi6VR646Eqzw9yg80nYsocJd01PTtEwiNCQBfTke-U9GpRXOxHN9cjRNzZ5z6w";
const headers = {
    // "Authorization":`basic ${btoa(`${user}:${pass}`)}`,
    "Content-Type":"application/json",
    "Authorization":`Bearer ${token}`
}

const userData = {
    username:'alokg252',
    password:'pass@123',
    role:'ROLE_ADMIN'
}

const payload = {
  name: 'alokg252',
  email: 'akg@reactor.com',
  mobile: '556r675765778',
  user: { id: 2},
  todos: [
    { title: 'security', body: 'jwt'},
    { title: 'security', body: 'explore more',}
  ]
}
const taskData = {
    task:"test task",
    cron:"0 */30 * * *"
}

function POST(){
    fetch(url,{
        method:"POST",
        headers: headers,
        body:JSON.stringify(taskData)
    }).then(res => res.json())
    .then(res => console.log(res))
}

function PUT(){
    fetch(url,{
        method:"PUT",
        headers: headers,
        body:JSON.stringify(userData)
    }).then(res => res.json())
    .then(res => console.log(res))
}

function GET(){
    fetch(url, {
        method:"GET",
        headers:headers,
    }).then(res => res.status === 200 ? res.json() : res.text())
    .then(res => console.log(res))
}

function DELETE(){
    fetch(url, {
        method:"DELETE",
        headers:headers,
    }).then(res => res.status === 200 ? res.json() : res.text())
    .then(res => console.log(res))
}

GET()