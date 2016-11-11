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

function delete(url) {
	return fetch(url, {
	  method: "DELETE",
	  credentials: "include",
	  headers: new Headers({
	    "Content-Type": "application/json"
	  })
	}).then(function (response) {
	  window.location.replace(response);
	}).then(function (successful) {
	  if (!successful) {
	    error("Cannot delete image.");
	    return;
	  } else {
	    window.location.replace(response);
	  }
	});
}

function upvote(rating) {
  return function () {
    payload = {
      rating: rating
    };
    fetch(`${window.location.pathname}/rating`, {
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
}

(function () {
  document.getElementById("upvote").addEventListener("click", upvote(true));
  document.getElementById("downvote").addEventListener("click", upvote(false));
  document.getElementById("post-button").addEventListener("click", post);
  document.getElementById("post-input").addEventListener("keypress", function (ev) {
    if (ev.keyCode === 13) {
      post();
    }
  })

})();

