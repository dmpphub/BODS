$(document).ready(function () {
		$('.success').pieChart({
            barColor: '#00cc00',
            trackColor: '#eee',
            lineCap: 'round',
            lineWidth: 10,
            onStep: function (from, to, percent) {
                $(this.element).find('.pie-value').text(Math.round(percent) + '%');
            }
        });
		
		 $('.error').pieChart({
	            barColor: '#e62e00',
	            trackColor: '#eee',
	            lineCap: 'round',
	            lineWidth: 10,
	            onStep: function (from, to, percent) {
	                $(this.element).find('.pie-value').text(Math.round(percent) + '%');
	            }
	        });
		 
		    $('#inner-content-div').slimScroll({
		        height: '410px'
		    });
    });