function initializeBookingCalendar(bookedDates, pricePerDay) {
    let startDate = null;

    function isDateBooked(date) {
        const ds = $.datepicker.formatDate('yy-mm-dd', date);
        return bookedDates.includes(ds);
    }

    function disableStart(date) {
        if(isDateBooked(date))
            return [false, ''];
        else
            return [true, ''];
    }

    function disableEnd(date) {
        if (!startDate || date < startDate) return [false, ''];
        let d = new Date(startDate);
        while (d <= date) {
            if (isDateBooked(d)) return [false, ''];
            d.setDate(d.getDate() + 1);
        }
        return [true, ''];
    }

    function updateTotals() {
        const f = $("#from").datepicker('getDate');
        const t = $("#to").datepicker('getDate');
        if (f && t && t >= f) {
            const timeDiff = t.getTime() - f.getTime();
            const diffDays = Math.floor(timeDiff / (1000 * 60 * 60 * 24)) + 1;
            const totalPrice = diffDays * pricePerDay;
            $("#totalDays").text(diffDays);
            $("#totalPrice").text(`$${totalPrice.toFixed(2)}`);
        } else {
            $("#totalDays").text("0");
            $("#totalPrice").text("$0.00");
        }
    }

    $('#from').datepicker({
        dateFormat: 'yy-mm-dd',
        minDate: 0,
        beforeShowDay: disableStart,
        onSelect: function () {
            startDate = $(this).datepicker('getDate');
            const minDate = new Date(startDate);
            minDate.setDate(minDate.getDate() + 1);

            $('#to').val('').datepicker('option', {
                minDate: minDate,
                beforeShowDay: disableEnd
            });
            updateTotals();
        }
    });

    $('#to').datepicker({
        dateFormat: 'yy-mm-dd',
        beforeShowDay: disableEnd,
        onSelect: updateTotals
    });

    $('#bookingForm').submit(function (e) {
        if (!$('#from').val() || !$('#to').val()) {
            e.preventDefault();
            alert("Please select both pick-up and return dates.");
        }
    });
}

$(function () {
    if (bookedDates && pricePerDay) {
        initializeBookingCalendar(bookedDates, pricePerDay);
    }
});

