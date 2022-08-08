


    function getFormInputValues()
    {
        let name = document.getElementById('nameInput').value;
        let password = document.getElementById('passwordInput').value;
        // alert(name + ' - ' + password)
        login(name, password);

    }

    function pageRefresh(){
        if (isLoggedIn()){
            location.replace("http://localhost:8080/index.html");
            // window.location.href = "http://localhost:8080/index.html";
        } else {
            location.replace("http://localhost:8080/login.html")
            // alert("Naam en of wachtwoord was incorrect.")
        }
    }

    function isLoggedIn(){
        return window.localStorage.getItem("myJWT") !== null;
    }

    async function login(name, password)
    {
        // if (name === 'Hans' && password === '123') {
        //     location.replace("http://localhost:63342/IPASS/viewLaag/index.html/");
        //     // alert(name + ' - ' + password);
        //
        // }

        let response = await fetch("restservices/gebruiker/verifieer", {method:"POST", headers:{'Content-Type': 'application/json'}, body: JSON.stringify({
            name:  name,
            password: password
        })})

        if (response.status === 200) {
            let myJson = await response.json();
            window.localStorage.setItem("myJWT", myJson.JWT);
            pageRefresh();
        } else {
            console.log("login failed")
        }
    }
