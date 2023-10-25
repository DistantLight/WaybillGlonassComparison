var primaryElements = document.getElementsByClassName('btn btn-primary');
var confirmIt = function (e) {
    if (!confirm('Are you sure?')) e.preventDefault();
};
for (var i = 0, l = primaryElements.length; i < l; i++) {
    primaryElements[i].addEventListener('click', confirmIt, false);
}

var warningElements = document.getElementsByClassName('btn btn-warning');
var confirmIt = function (e) {
    if (!confirm('Are you sure?')) e.preventDefault();
};
for (var i = 0, l = warningElements.length; i < l; i++) {
    warningElements[i].addEventListener('click', confirmIt, false);
}

var dangerElements = document.getElementsByClassName('btn btn-danger');
var confirmIt = function (e) {
    if (!confirm('Are you sure?')) e.preventDefault();
};
for (var i = 0, l = dangerElements.length; i < l; i++) {
    dangerElements[i].addEventListener('click', confirmIt, false);
}

var secondaryElements = document.getElementsByClassName('btn btn-secondary');
var confirmIt = function (e) {
    if (!confirm('Are you sure?')) e.preventDefault();
};
for (var i = 0, l = secondaryElements.length; i < l; i++) {
    secondaryElements[i].addEventListener('click', confirmIt, false);
}