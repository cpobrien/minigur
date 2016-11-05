(function () {
  document.getElementById("submit").addEventListener("click", function () {
    var payload = {
      title: document.getElementById("title").value,
      image: document.getElementById("image-file").files[0]
    };
    if (payload.title === "" || payload.file === undefined) {
      return;
    }
    fetch("/image", {
      method: "POST",
      body: payload
    });
  });
})();
