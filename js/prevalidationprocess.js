var prevalidationViewList;

$(document).ready(function () {
	$('#inner-content-div').slimScroll({
	    height: '410px'
	});
	callPrevalidation();
});

function easyPieChart() {
	
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
	 
	var chart = window.chart = $('.chart').data('easyPieChart');
	$('.js_update').on('click', function() {
		chart.update(Math.random()*200-100);
	});
}

function renderSuccessChart(successPrecentage) {
	/*var percent = $('#totalSuccessChartSpan').text();*/
	//var percent = Trim(successPrecentage);
	if ($.isNumeric(successPrecentage)) {
		$('#totalSuccessChart').data('easyPieChart').update(successPrecentage);
	} else {
		$('#totalSuccessChart').data('easyPieChart').update(0);
	}
}

function renderErrorChart(errorPrecentage) {
	/*var percent = $('#totalErrorChartSpan').text();*/
	//var percent = Trim(errorPrecentage);
	if ($.isNumeric(errorPrecentage)) {
		$('#totalErrorChart').data('easyPieChart').update(errorPrecentage);
	} else {
		$('#totalErrorChart').data('easyPieChart').update(0);
	}
}


	function callPrevalidation() {
		$.ajax({
			 type: "POST",
		     dataType: "text",
		     url: "/bods/PrevalidationProcessFetchMapping.etl",
		     success: function(data) {
		    	 if(data != null && data != '') {
		    		 alert($.parseJSON(data));
		    		 var conditionVO = $.parseJSON(data);
		    		 prevalidationViewList = conditionVO.configuratorVO.prevalidationStatusVO;
		    		 formValidationDisplayScreen(prevalidationViewList);
		    	 }
		     }, error: function(xhr, status, error) {
		            console.log(xhr);
		            console.log(status);
		            console.log(error);
		       }
		})
	}

	function formValidationDisplayScreen(prevalidationViewList) {
		var formValidtaionStr = '';
		var errorCount='';
		var errorPrecentage='';
		var successCount='';
		var successMsg='';
		var errorMsg='';
		var successPrecentage = '';
		var attributeName = '';
		var prevalFrameDivObject = $('#inner-content-div');
		$(prevalFrameDivObject).empty();
		for (var i = 0; i < prevalidationViewList[0].length; i++) {
			attributeName = prevalidationViewList[0][i].pv_attribute;
			errorCount = prevalidationViewList[0][i].errorCount;
			successCount = prevalidationViewList[0][i].successCount;
			successPrecentage = prevalidationViewList[0][i].successPrecentage;
			errorPrecentage = prevalidationViewList[0][i].errorPrecentage;
			successMsg = prevalidationViewList[0][i].successMsg;
			errorMsg = prevalidationViewList[0][i].errorMsg;
			formValidtaionStr = '';
			/*if(i > 0) {*/
				formValidtaionStr = '<br\>';
				formValidtaionStr += '<br\>';
				formValidtaionStr += '<div class="div-table-row-progress">';
			/*} else {
				formValidtaionStr = '<div class="div-table-row-progress">';
			}*/
			formValidtaionStr += '	<div class="div-table-col" style="width=50px;">';
			formValidtaionStr += '  	<table>';
			formValidtaionStr += '  		<tr>';
			formValidtaionStr += '  			<th>'+attributeName+'</th>';
			formValidtaionStr += '			</tr>';
			if(errorCount > 0) {
				formValidtaionStr += '  		<tr>';
				formValidtaionStr += '  			<td>'+errorMsg+'</td>';
				formValidtaionStr += '			</tr>';
			} else {
				formValidtaionStr += '  		<tr>';
				formValidtaionStr += '  			<td>'+successMsg+'</td>';
				formValidtaionStr += '			</tr>';
			}
			
			formValidtaionStr += '		</table>';
			formValidtaionStr += '	</div>';
			formValidtaionStr += '	<div class="div-table-col">';
			formValidtaionStr += '		<div  class="pie-title-center success" data-percent="'+successPrecentage+'" style="margin-left: 100px;margin-top: 2px;">';
			formValidtaionStr += '		<span style="margin-left: -100px;margin-top: -23px;" class="pie-value"></span></div>';
			formValidtaionStr += '	</div>';
			formValidtaionStr += '	<div class="div-table-col">';
			formValidtaionStr += '		<div  class="pie-title-center error" data-percent="'+errorPrecentage+'" style="margin-top: 2px;">';
			formValidtaionStr += '		<span style="margin-left: -100px;margin-top: -23px;" class="pie-value"></span></div>';
			formValidtaionStr += '</div>';
			formValidtaionStr += '<br\>';
			formValidtaionStr += '<br\>';
			formValidtaionStr += '<br\>';
			prevalFrameDivObject.append(formValidtaionStr);
			easyPieChart();
			//renderSuccessChart(successPrecentage);
			//renderErrorChart(errorPrecentage);
		}
	}
