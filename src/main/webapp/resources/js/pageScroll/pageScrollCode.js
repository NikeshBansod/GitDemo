
scrollingElement = (document.scrollingElement || document.body)
function scrollSmoothToBottom () {
	   $(scrollingElement).animate({
	      scrollTop: document.body.scrollHeight
	   }, 200);
}

//Require jQuery
function scrollSmoothToTop () {
   $(scrollingElement).animate({
      scrollTop: 0
   }, 200);
}
