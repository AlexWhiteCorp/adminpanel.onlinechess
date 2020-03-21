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

window.resizeContent = function () {
    if(!isSmallScreen()){
        document.getElementById('content-wrapper').style.maxHeight = document.body.offsetHeight - window.header.offsetHeight + 'px';
        document.getElementById('content-wrapper').style.minHeight = document.body.offsetHeight - window.header.offsetHeight + 'px';
    }
};