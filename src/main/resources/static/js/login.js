function get(name) {
    if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
        return decodeURIComponent(name[1]);
}

if (get('log') === 'true') {
    $(window).on('load', function () {
        $('#myModal').modal('show');
    });
}