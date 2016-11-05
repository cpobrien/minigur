function sendData(location) {
  return function () {
    var payload = {
      username: document.getElementById("username").value,
      password: document.getElementById("password").value
    };
    if (!payload.username) {
      error("No username has been inputted.");
      return;
    }
    if (!payload.password) {
      error("No password has been inputted.");
      return;
    }
    fetch(location, {
      method: "POST",
      body: JSON.stringify(payload),
      headers: new Headers({
        "Content-Type": "application/json"
      })
    }).then(function (response) {
      return response.json();
    }).then(function (successful) {
      if (!successful) {
        error("Invalid username or password.");
      }
    });
  }
}

function error(message) {
  document.getElementById("error-holder").innerHTML = `
   <div class="alert alert-danger" role="alert">
    ${message}
   </div>
  `;
}
(function () {
  document.getElementById("login").addEventListener("click", sendData("/login"));
  document.getElementById("register").addEventListener("click", sendData("/user_api"));
})();