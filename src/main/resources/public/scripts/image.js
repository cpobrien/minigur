function post() {
  var payload = {
    text: document.getElementById("post-input").value
  };
  console.log(payload);
  fetch(`${window.location.pathname}/comment`, {
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
      return;
    }
    window.location.reload();
  });
}

(function() {
  document.getElementById("post-button").addEventListener("click", post);
  document.getElementById("post-input").addEventListener("keypress", function(ev) {
    if (ev.keyCode === 13) {
      post();
    }
  })

})();

