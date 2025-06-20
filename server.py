import socket

HOST = '0.0.0.0'
PORT = 8080

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST,PORT))
server.listen(1)

print("server is listening at port ", PORT)

html = """
<div style="display:flex;height:100vh;align-items:center;">
<h1 style="color:royalblue;text-align:center;justify-content:center;font-size:40px;">Welcome to Socket</h1>
</div>
"""

while True:
    client_socket, addr = server.accept()
    req = client_socket.recv(1024).decode()
    print("\n---Server Received---\n"+req)
    
    res = ("HTTP/1.1 200 OK\r\n"
           "Content-Type: text/html\r\n"
           f"Content-Length: {len(html)}\r\n\r\n"
           f"{html}"
        )
    # res = "welcome"
    client_socket.sendall(res.encode())
    client_socket.close()
