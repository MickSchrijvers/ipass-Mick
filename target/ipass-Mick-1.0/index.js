if (!isLoggedIn()){
    pageRefresh();
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

//Checkt of iemand ingelogd is door te kijken of de myJWT key een waarde heeft.
function isLoggedIn(){
    return window.localStorage.getItem("myJWT") !== null;
}

function logout(){
    window.localStorage.removeItem("myJWT");
    pageRefresh();
}