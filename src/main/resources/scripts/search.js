function executeSearch() {
	var filter = $('.search-form').find('#filter').val();
	var query = $('.search-form').find('#query').val();
	if (query === null || query === '') {
		alert('Please enter a query.');
	} else {
		$.ajax({
		   url: '/search/',
		   type: 'POST',
		   data: {
		      filter: filter,
		      query: query 
		   },
		   success: function(data) {
		     
		   },
		   error: function() {
		      
		   },
		});
	}


}