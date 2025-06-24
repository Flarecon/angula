const user = "alokg252";
const pass = "pass@123";
const url = "http://127.0.0.1:8080/cache/client";
const headers = {
    Authorization:`basic ${btoa(`${user}:${pass}`)}`,
    "Content-Type":"application/json"
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

function POST(){
    fetch(url,{
        method:"POST",
        headers: headers,
        body:JSON.stringify(payload)
    }).then(res => res.json())
    .then(res => console.log(res))
}

function PUT(){
    fetch(url,{
        method:"PUT",
        headers: headers,
        body:JSON.stringify(payload)
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

DELETE()