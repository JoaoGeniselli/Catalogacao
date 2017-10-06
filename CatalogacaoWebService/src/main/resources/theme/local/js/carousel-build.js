var counter = 0;
function buildSliderForPhotoList(photoList) {

    var context = '/catalogacao/web/image/';

    var html =
            '<div id="myCarousel' + ++counter + '" class="carousel slide" style="height:100%;" data-ride="carousel">' +
            '<div class="carousel-inner" role="listbox">';

    html = html + '<ol class="carousel-indicators">';
    for (var index in photoList) {
        if (index === "0") {
            html = html + '<li data-target="#myCarousel' + counter + '" data-slide-to="' + index + '" class="active"></li>';
        } else {
            html = html + '<li data-target="#myCarousel' + counter + '" data-slide-to="' + index + '"></li>';
        }
    }
    html = html + '</ol>'; // end of ol

    for (var index in photoList) {
        var photo = photoList[index];

        var div;
        if (index === "0") {
            div = '<div style="height:200px" class="item active">';
        } else {
            div = '<div class="item" style="height:200px">';
        }

        div = div + '<img style="height: auto; width: auto" src="' + context + photo['filepath'] +'"/>';
        div = div + '</div>'; // end of item

        html = html + div;
    }

    html = html + '</div>'; // end of list box

    html = html +
            '<a class="left carousel-control" href="#myCarousel' + counter + '" data-slide="prev">' +
                '<span class="glyphicon glyphicon-chevron-left"></span>' +
                '<span class="sr-only">Previous</span>' +
            '</a>' +
            '<a class="right carousel-control" href="#myCarousel' + counter + '" data-slide="next">' +
                '<span class="glyphicon glyphicon-chevron-right"></span>' +
                '<span class="sr-only">Next</span>' +
            '</a>' +
      '</div>'; // end of myCarousel
    return html;
}
