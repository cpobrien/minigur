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
      credentials: "include",
      body: JSON.stringify(payload),
      headers: new Headers({
        "Content-Type": "application/json"
      })
    }).then(function (response) {
      return response.json();
    }).then(function (successful) {
      if (!successful) {
        error("Invalid username or password.");
        return;
      }
      window.location.reload();
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
  document.getElementById("register").addEventListener("click", sendData("/user_api/"));
  document.getElementById("login").addEventListener("click", sendData("/login"));
  document.querySelectorAll("#password, #username").forEach(function(element) {
    element.addEventListener("keypress", function(e) {
      if (e.keyCode === 13) {
        sendData("/login")();
      }
    });
  });
})();