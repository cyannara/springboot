/**
 var main = {
  init : function(){},
  save : function(){},
  update : function(){}
 }
 */
var main = {
  init : function(){
    var _this = this;
    $("#btn-save").on('click', function(){
      _this.save();
    })
    
    $("#btn-update").on('click', function(){
      _this.update();
    })
    
    $("#btn-delete").on('click', function(){
      _this.delete();
    })    
  },  ////end of init
  
  
  save : function(){
	var data = {
      title : $('#title').val(),
      author : $('#author').val(),
      content : $('#content').val()  
	}

    $.ajax({
      type:'post',
      url:'/api/v1/posts',
      dataType : 'json',
      contentType : 'application/json; charset=utf-8',
      data : JSON.stringify(data)
    }).done(function(){
      alert('글이 등록되었습니다.');
      window.location.href = '/posts';
    }).fail(function(error){
      alert(JSON.stringify(error));
    })
  },  //end of save
 
  update : function(){
  	var id = $('#id').val();

	var data = {
      title : $('#title').val(),
      content : $('#content').val()  
	}

    $.ajax({
      type:'put',
      url:'/api/v1/posts/'+id,
      dataType : 'json',
      contentType : 'application/json; charset=utf-8',
      data : JSON.stringify(data)
    }).done(function(){
      alert('글이 수정되었습니다.');
      window.location.href = '/posts';
    }).fail(function(error){
      alert(JSON.stringify(error));
    })
  },   //end of update

  delete : function(){
  	var id = $('#id').val();

    $.ajax({
      type:'delete',
      url:'/api/v1/posts/'+id,
      dataType : 'json',
      contentType : 'application/json; charset=utf-8'
    }).done(function(){
      alert('글이 삭제되었습니다.');
      window.location.href = '/posts';
    }).fail(function(error){
      alert(JSON.stringify(error));
    })
  }   //end of delete 
};  

$(function(){
	console.log("aaa");
	main.init(); 
})