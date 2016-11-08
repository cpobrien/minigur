function executeSearch() {
	var query = $('.query-container').find('#query').val();
	var everything = $('.filter-container').find('#everything').is(":checked");
	var title = $('.filter-container').find('#title').is(":checked");
	var comment = $('.filter-container').find('#comment').is(":checked");
	var tag = $('.filter-container').find('#tag').is(":checked");
	var user = $('.filter-container').find('#user').is(":checked");

	var filters = {};
	filters.title = false;
	filters.comment = false;
	filters.tag = false;
	filters.user = false;
	if (everything === true) {
		filters.title = true;
		filters.comment = true;
		filters.tag = true;
		filters.user = true;
	} else {
		if (title === true) {
			filters.title = true;
		} 
		if (comment === true) {
			filters.comment = true;
		} 
		if (tag === true) {
			filters.tag = true;
		} 
		if (user === true) {
			filters.user = true;
		}
	} 
	
	if (query === null || query === '') {
		alert('Please enter a query.');
	} else {
		$.ajax({
		   url: '/search/',
		   type: 'POST',
		   data: {
		   	  searchString: query,
		      searchTitle: filters.title,
		      searchComment: filters.comment,
		      searchTag: filters.tag,
		      searchUsername: filters.user
		   },
		   success: function(data) {
		     
		   },
		   error: function() {
		      
		   },
		});
	}


}