/**
 *
 */
(function(){

    var timestamp = document.querySelector('#timestamp');

    setInterval(updateTimestamp, 20);

    function updateTimestamp() {
        timestamp.innerHTML = new Date().getTime();
    }
})();