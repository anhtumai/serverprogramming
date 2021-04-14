function ensurePasswordMatch() {
    let password = document.getElementById("password").value;
    let matchPassword = document.getElementById("matchPassword").value;

    if (password !== matchPassword)
    {
        alert("Password does not match!");
        return false;
    }

    return true;
}

function ensureValidEmail() {
    let email = document.getElementById("email").value;
    let emailRegexp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!emailRegexp.test(email)) {
        alert("Email is not valid!");
        return false;
    }
    return true;
}

function ensure() {
    return ensurePasswordMatch() && ensureValidEmail();
}

window.onload = () => {
    document.getElementById("registration").onsubmit = () => ensure();
}
if ( window.history.replaceState ) {
    window.history.replaceState( null, null, window.location.href );
}