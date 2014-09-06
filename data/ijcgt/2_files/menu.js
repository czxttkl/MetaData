$(function(){
  var nb = $('#navbtn');
  var n = $('#topnav #article_list');
  
  $(window).on('resize', function(){
    
    if($(this).width() < 570 && n.hasClass('keep-nav-closed')) {
      // if the #article_list menu and #article_list button are both visible,
      // then the responsive #article_list transitioned from open to non-responsive, then back again.
      // re-hide the #article_list menu and remove the hidden class
      $('#topnav #article_list').hide().removeAttr('class');
    }
    if(nb.is(':hidden') && n.is(':hidden') && $(window).width() > 569) {
      // if the navigation menu and #article_list button are both hidden,
      // then the responsive #article_list is closed and the window resized larger than 560px.
      // just display the #article_list menu which will auto-hide at <560px width.
      $('#topnav #article_list').show().addClass('keep-nav-closed');
    }
  }); 
  
  $('#navbtn').on('click', function(e){
    e.preventDefault(); // stop all hash(#) anchor links from loading
  });
  
  $('#navbtn').on('click', function(e){
    e.preventDefault();
    $("#topnav #article_list").slideToggle(350);
  });
  
});