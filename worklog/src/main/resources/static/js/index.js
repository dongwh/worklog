$(function() {
    cmainFrame();
});

function cmainFrame() {
    var hmain = document.getElementById("mainFrame");
    var bheight = document.documentElement.clientHeight;
    hmain.style.width = '100%';
    hmain.style.height = (bheight+50) + 'px';
}

function menuOnclick(mUrl,menuPname,menuName){
    //changeBreadcrumbs(obj);
    var hmain = document.getElementById("mainFrame");
    hmain.src = mUrl;
}
function changeBreadcrumbs(obj){
    $(obj).parent().addClass("active");
}