var prevalidationViewList;

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
		    
		    callPrevalidation();
    });

	function callPrevalidation() {
		$.ajax({
			 type: "POST",
		     dataType: "text",
		     url: "/bods/PrevalidationProcessFetchMapping.etl",
		     success: function(data) {
		    	 alert('data' +  data);
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
		for (var i = 0; i < prevalidationViewList.length; i++) {
			attributeName = prevalidationViewList[i][i].attributeName;
			errorCount = prevalidationViewList[i][i].errorCount;
			successCount = prevalidationViewList[i][i].successCount;
			successPrecentage = prevalidationViewList[i][i].successPrecentage;
			errorPrecentage = prevalidationViewList[i][i].errorPrecentage;
			successMsg = prevalidationViewList[i][i].successMsg;
			errorMsg = prevalidationViewList[i][i].errorMsg;
			formValidtaionStr = '<div class="div-table-row-progress">';
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
			prevalFrameDivObject.append(formValidtaionStr);
		}
	}
