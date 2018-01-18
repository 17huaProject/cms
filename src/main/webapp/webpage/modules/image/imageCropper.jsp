
  <!-- 图片裁剪缩略 -->
  <link rel="stylesheet" href="cms/static/cropper-master/css/bootstrap.min.css">
  <link rel="stylesheet" href="cms/static/cropper-master/css/cropper.css">
  <link rel="stylesheet" href="cms/static/cropper-master/css/main.css">

  <script src="cms/static/cropper-master/js/jquery-3.2.1.slim.min.js"></script>
  <script src="cms/static/cropper-master/js/tether.min.js"></script>
  <script src="cms/static/cropper-master/js/bootstrap.min.js"></script>
  <script src="cms/static/cropper-master/js/common.js"></script>
  <script src="cms/static/cropper-master/js/cropper.js"></script>
  <script src="cms/static/cropper-master/js/main.js"></script>

  <!-- Content -->
  <div class="container"> 
    <div class="row">
      <div class="col-md-9">
        <div class="img-container">
          <img id="image" src="images/picture.jpg" alt="Picture">
        </div>
      </div>
      <div class="col-md-3">
        <!-- <h3>Preview:</h3> -->
        <div class="docs-preview clearfix">
          <div class="img-preview preview-lg"></div>
          <div class="img-preview preview-md"></div>
        </div>

        <!-- <h3>Data:</h3> -->
        <div class="docs-data">
          <div class="input-group input-group-sm">
            <label class="input-group-addon" for="dataX">X</label>
            <input type="text" class="form-control" id="dataX" placeholder="x">
            <span class="input-group-addon">px</span>
          </div>
          <div class="input-group input-group-sm">
            <label class="input-group-addon" for="dataY">Y</label>
            <input type="text" class="form-control" id="dataY" placeholder="y">
            <span class="input-group-addon">px</span>
          </div>
          <div class="input-group input-group-sm">
            <label class="input-group-addon" for="dataWidth">width</label>
            <input type="text" class="form-control" id="dataWidth" placeholder="width">
            <span class="input-group-addon">px</span>
          </div>
          <div class="input-group input-group-sm">
            <label class="input-group-addon" for="dataHeight">height</label>
            <input type="text" class="form-control" id="dataHeight" placeholder="height">
            <span class="input-group-addon">px</span>
          </div>         
        </div>
		
		<div class="docs-toggles">
			<div class="btn-group d-flex flex-nowrap" data-toggle="buttons">
			  <label class="btn btn-primary active">
				<input type="radio" class="sr-only" id="aspectRatio0" name="aspectRatio" value="1.7777777777777777">
				<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="aspectRatio: 16 / 9">
				  16:9
				</span>
			  </label>
			  <label class="btn btn-primary">
				<input type="radio" class="sr-only" id="aspectRatio1" name="aspectRatio" value="1.3333333333333333">
				<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="aspectRatio: 4 / 3">
				  4:3
				</span>
			  </label>
			  <label class="btn btn-primary">
				<input type="radio" class="sr-only" id="aspectRatio2" name="aspectRatio" value="1">
				<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="aspectRatio: 1 / 1">
				  1:1
				</span>
			  </label>
			  <label class="btn btn-primary">
				<input type="radio" class="sr-only" id="aspectRatio3" name="aspectRatio" value="0.6666666666666666">
				<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="aspectRatio: 2 / 3">
				  2:3
				</span>
			  </label>
			  <label class="btn btn-primary">
				<input type="radio" class="sr-only" id="aspectRatio4" name="aspectRatio" value="NaN">
				<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="aspectRatio: NaN">
				  Free
				</span>
			  </label>
			</div>
		</div>
		
		<div class="docs-buttons">
			<div class="btn-group">
				<label class="btn btn-primary btn-upload" for="inputImage">
					<input type="file" class="sr-only" id="inputImage" name="file" accept=".jpg,.jpeg,.png,.gif">
					<span class="docs-tooltip" data-toggle="tooltip" data-animation="false" title="Import image with Blob URLs">
						<span class="fa fa-upload">select</span>
					</span>
				</label>		  
			</div>
			<div class="btn-group">
				<label class="btn btn-primary btn-upload" for="uploadImage">
					<input type="button" class="sr-only" id="uploadImage" >
					<span class="docs-tooltip">
						<span class="fa fa-upload">upload</span>
					</span>
				</label>		  
			</div>
			<!-- Show the cropped image in modal -->
			<div class="modal fade docs-cropped" id="getCroppedCanvasModal" aria-hidden="true" aria-labelledby="getCroppedCanvasTitle" role="dialog" tabindex="-1">
				<div class="modal-dialog">
					<div class="modal-content">
						
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<a class="btn btn-primary" id="download" href="javascript:void(0);" download="cropped.jpg">Download</a>
						</div>
					</div>
				</div>
			</div><!-- /.modal -->
		</div><!-- /.docs-buttons -->
      </div>

    </div>
	
  </div>


