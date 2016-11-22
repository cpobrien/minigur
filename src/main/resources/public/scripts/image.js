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

function deleteImage(url) {
	return fetch(`/image${window.location.pathname}/`, {
	  method: "DELETE",
	  credentials: "include",
	}).then(function (response) {
    window.location.href = '/';
	});
}

function enableEdit(id) {
	$('#'+id).find('.blockquote').attr('contenteditable', 'true');
	$('#'+id).find('#edit-comment').addClass('editing').html('SUBMIT');
}

function submitEdit(id) {
  var payload = {
    text: $('#'+id).find('p').html()
  };
  fetch(`${window.location.pathname}/comment/`+id, {
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

function deleteComment(e) {
return fetch(`${window.location.pathname}/comment/${$(this).closest('.margin').attr('id')}`, {
    method: "DELETE",
    credentials: "include",
    headers: new Headers({
      "Content-Type": "application/json"
    })
  }).then(function (response) {
    return response.json();
  }).then(function (successful) {
    if (!successful) {
      return;
    }
    //that.parentNode.remove();
    window.location.reload();
  });
};

function deleteTag() {
  console.log(this.parentNode.getAttribute("tag-name").valueOf().trim());
  console.log(window.location.pathname.slice(1));
  console.log(`/tag/${this.parentNode.getAttribute("tag-name").valueOf().trim()}/`);
  var payload = {
    imageId: window.location.pathname.slice(1)
  };
  var that = this;
  console.log(payload);
  return fetch(`/tag/${this.parentNode.getAttribute("tag-name").valueOf().trim()}/`, {
    method: "DELETE",
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
    that.parentNode.remove();
    //window.location.reload();
  });
}

(function () {
  var deleteElement = document.getElementById("delete-image");
  if (deleteElement) {
    deleteElement.addEventListener("click", deleteImage);
  }


  $('#edit-comment').on('click', function(){
    var id = $(this).closest('.margin').attr('id');
    if ($(this).hasClass('editing')){
        submitEdit(id);
    } else {
        enableEdit(id);
    }
  });

  [].forEach.call(document.getElementsByClassName("delete-comment-btn"), function(e) {
      e.addEventListener("click", deleteComment);
    });

  document.getElementById("upvote").addEventListener("click", upvote(true));
  document.getElementById("downvote").addEventListener("click", upvote(false));
  document.getElementById("post-button").addEventListener("click", post);
  [].forEach.call(document.getElementsByClassName("remove-tag"), function(e) {
    e.addEventListener("click", deleteTag);
  });
  document.getElementById("post-input").addEventListener("keypress", function (ev) {
    if (ev.keyCode === 13) {
      post();
    }
  });

  document.getElementById("logout").addEventListener("click", function () {
    fetch("/signout", {
      method: "POST",
      credentials: "include"
    }).then(function (response) {
      window.location.href = '/';
    })
  });

})();

