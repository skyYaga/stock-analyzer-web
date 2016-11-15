$(document).ready(function () {
    var buyCount = 0;
    var holdCount = 0;
    var sellCount = 0;
    var yahooRating = 0;

    var calculateDibaRating = function () {
        var totalCount = buyCount + holdCount + sellCount;
        var dibaRating = (buyCount * 1 + holdCount * 2 + sellCount * 3) / totalCount;
        var ratingFormatted = dibaRating.toFixed(2);

        $("#dibaRating").text(ratingFormatted.replace(".", ","));
        $("#analystRating").val(ratingFormatted)
    };

    var calculateYahooRating = function () {
        var yahooRatingCalculated = ((yahooRating - 1) / 4) * 2 + 1;
        var ratingFormatted = yahooRatingCalculated.toFixed(2);

        $("#yahooRatingCalculated").text(ratingFormatted.replace(".", ","));
        $("#analystRating").val(ratingFormatted)
    };

    $("#buyRating").bind('keyup change', function (event) {
        console.log("triggered");
        buyCount = event.target.valueAsNumber;
        calculateDibaRating();
    });

    $("#holdRating").bind('keyup change', function (event) {
        holdCount = event.target.valueAsNumber;
        calculateDibaRating();
    });

    $("#sellRating").bind('keyup change', function (event) {
        sellCount = event.target.valueAsNumber;
        calculateDibaRating();
    });

    $("#yahooRating").bind('keyup change', function (event) {
        yahooRating = event.target.valueAsNumber;
        calculateYahooRating();
    });
});