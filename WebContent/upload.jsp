<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Upload File</title>

	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<style>
		#progressbar {margin-top: 30px;}
		.progress-label {font-weight: bold; text-shadow: 1px 1px 0 #fff;}
		.ui-dialog-titlebar-close {display: none;}
		.ui-dialog-titlebar {display: none;}
 	</style>
</head>
<body>

<div id="dialog" title="File Upload">
  <div class="progress-label">Starting upload...</div>
  <div id="progressbar"></div>
</div>

<form action="<%=request.getContextPath() %>/fileUpload" method="POST" enctype="multipart/form-data" id="fileUploadForm" name="fileUploadForm">
	Select File To Upload:
	<input type="file" width="600px" multiple id="fileToUpload" name="fileToUpload">
	<button id="uploadBtn" onclick="uploadFile();">Upload</button>
</form>
<form action="<%=request.getContextPath() %>/upload" method="POST" id="uploadForm" name="uploadForm">
</form>

<script>
		var progressbar = $("#progressbar");
		var progressLabel = $(".progress-label");

		var dialog = $("#dialog").dialog({
			modal: true,
			autoOpen: false,
			closeOnEscape: false,
			resizable: false,
			open: function() {
				progress();
			}
		});

		progressbar.progressbar({
			value: false,
			change: function() {
				progressLabel.text( "Current Progress: " + progressbar.progressbar( "value" ) + "%" );
			},
			complete: function() {
				progressbar.hide();
				progressLabel.text( "Upload Complete!" );
				setTimeout(function() {
					closeUpload();
				}, 2000);
			}
		});

		function progress() {
			var url = '<%=request.getContextPath() %>/progress';
			$.ajax({
				url: url,
				type: 'POST',
				dataType: 'json',
				success: function (data) {
					var isSuccess = data.isSuccess;
					var bytesRead = data.bytesRead;
					var contentLength = data.contentLength;
					var percentDone = data.percentDone;

					progressbar.progressbar( "value", percentDone);
					if (percentDone <= 99) {
						progress();
					}
				}
			});
		}

		function closeUpload() {
			dialog.dialog("close");
			$("#uploadForm").submit();
		}

		function uploadFile() {
			dialog.dialog("open");

			var url = '<%=request.getContextPath() %>/fileUpload';
			var formData = new FormData($("#fileUploadForm"));
			$.ajax({
				url: url,
				type: 'POST',
				data: formData,
				success: function (data) {
					console.log("SUCCESS FILE UPLOAD AJAX");
				},
				cache: false,
				contentType: false,
				processData: false
			});
				
		}
	</script>

</body>
</html>