(function () {
  document.getElementById("logout").addEventListener("click", function () {
    fetch("/signout", {
      method: "POST",
      credentials: "include",
    }).then(function (response) {
      window.location.href = '/';
    })
  });
})();