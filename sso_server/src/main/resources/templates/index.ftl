<html>
    <head>Auth Center</head>

    <body>
        <div>
            <form id = "login" action = "/login" method="post">
                user name : <input type = "text" name = "username", value =  "root" />
                password  : <input type = "password" name = "password", value =  "root" />
                <input type = "hidden" name = "clientUrl", value =  "${clientUrl}" />
                <input type = "submit" value =  "Login" />
            </form>
        </div>

    </body>
</html>