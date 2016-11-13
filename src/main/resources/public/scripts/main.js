(function () {
  document.getElementById("logout").addEventListener("click", function () {
    fetch("/signout", {
      method: "POST",
      credentials: "include"
    }).then(function (response) {
      window.location.href = '/';
    })
  });

  function searchUsers() {
    var inputValue = document.getElementById("user-search").value.trim();
    fetch("/user_api/search/" + inputValue, {
      method: "POST",
      credentials: "include"
    }).then(function (response) {
      if (response.ok) {
        response.json().then(function(json) {
          buildUserTable(json);
        });
      }
    });
  }

  function buildUserTable(json) {
    var table = document.getElementById("users-table");
    table.innerHTML = '';
    if (json.length === 0) {
      var row = table.insertRow();
      var noResultsCell = row.insertCell(0);
      noResultsCell.setAttribute("align", "center");
      noResultsCell.innerHTML = "No users found."
      return;
    }
    var tableHead = table.createTHead();
    var th = document.createElement("th");
    th.innerHTML = "Username";
    tableHead.appendChild(th);
    th = document.createElement("th");
    th.innerHTML = "Admin";
    tableHead.appendChild(th);
    th = document.createElement("th");
    th.innerHTML = "Delete User";
    tableHead.appendChild(th);

    for (var i = 0; i < json.length; i++) {
      var row = table.insertRow();
      var usernameCell = row.insertCell(0);
      var adminCell = row.insertCell(1);
      var deleteCell = row.insertCell(2);
      var deleteBtn = document.createElement("button");
      deleteBtn.className = "btn btn-danger btn-sm";
      deleteBtn.setAttribute("username", json[i]["username"]);
      deleteBtn.addEventListener("click", deleteUser);
      deleteBtn.innerHTML = "Delete";
      usernameCell.innerHTML = json[i]["username"];
      adminCell.innerHTML = json[i]["admin"];
      deleteCell.appendChild(deleteBtn);
    }
  }

  function deleteUser() {
    var username = this.getAttribute("username");
    fetch("/user_api/" + username, {
      method: "DELETE",
      credentials: "include"
    }).then(function(response) {
      if (response.ok) {
        searchUsers();
      }
    });
  }

  var searchBtn = document.getElementById("search-btn");
  var searchInput = document.getElementById("user-search");
  if (searchBtn && searchInput) {
    searchBtn.addEventListener("click", searchUsers);
    searchInput.addEventListener("keypress", function(event) {
      if (event.keyCode === 13) {
        searchUsers();
      }
    });
  }


})();