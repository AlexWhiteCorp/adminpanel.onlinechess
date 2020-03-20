window.getClickedElement = function(e){
    if (navigator.userAgent.match('MSIE') || navigator.userAgent.match('Gecko')) {
        var elem = document.elementFromPoint(e.clientX,e.clientY);
    } else {
        var elem = document.elementFromPoint(e.pageX,e.pageY);
    }
    return elem.id;
}

window.isSmallScreen = function () {
    return window.innerWidth <= 1024;
}

window.getRestPath = function (page) {
  return '/rest/' + page;
};