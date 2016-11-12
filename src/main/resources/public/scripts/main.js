(function () {
  document.getElementById("logout").addEventListener("click", function () {
    fetch("/signout", {
      method: "POST",
      credentials: "include",
    }).then(function (response) {
      window.location.href = '/';
    })
  });

  function searchUsers() {
    var inputValue = document.getElementById("user-search").value.trim();
    fetch("/user_api/search/" + inputValue, {
      method: "POST"
    }).then(function (response) {
      if (response.ok) {
        response.json().then(function(json) {
          console.log(json);
        });
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