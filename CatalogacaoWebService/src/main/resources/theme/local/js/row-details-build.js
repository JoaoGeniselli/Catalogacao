import {buildSliderForPhotoList} from 'carousel-build';

function  formatAntDetail(d) {
  /*<![CDATA[*/
  var nest = /*[[${nest}]]*/ 'test';
  var antList = nest["ants"];
  var ant = antList[d.index()];
  /*]]>*/

  var html =
  '<div class="row">' +
    '<div class="col-md-8">' +
      '<p><strong class="col-md-3 text-right">Ordem: </strong> ' + ant["antOrder"] + '</p>' +
      '<p><strong class="col-md-3 text-right">Família: </strong> ' + ant["antFamily"] + '</p>' +
      '<p><strong class="col-md-3 text-right">Sub-família:</strong> ' + ant["antSubfamily"] + '</p>' +
      '<p><strong class="col-md-3 text-right">Gênero: </strong> ' + ant["antGenus"] + '</p>' +
      '<p><strong class="col-md-3 text-right">Sub-gênero: </strong> ' + ant["antSubgenus"] + '</p>' +
      '<p><strong class="col-md-3 text-right">Espécie: </strong> ' + ant["antSpecies"] + '</p>' +
    '</div>' +
  '<div class="col-md-4">';

  var carousel = buildSliderForPhotoList(ant['photos']);

  html = html + carousel;

  html = html +
  '<div th:replace="slider :: image-slider"></div>' +
  '</div>' +
  '</div>';

  return html;
}

$(document).ready(function() {
  var dt = $('#ants-table').DataTable( {
    responsive: true,
    "language": {
      "url": "/theme/vendor/datatables/lang/portuguese-brasil.lang"
    }
  } );

  // Array to track the ids of the details displayed rows
  var antDetailRows = [];

  $('#ants-table tbody').on( 'click', 'tr td.details-control', function () {
    var tr = $(this).closest('tr');
    var row = dt.row( tr );
    var idx = $.inArray( tr.attr('id'), antDetailRows );

    if ( row.child.isShown() ) {
      tr.removeClass( 'details' );
      row.child.hide();

      // Remove from the 'open' array
      antDetailRows.splice( idx, 1 );
    }
    else {
      tr.addClass( 'details' );

      row.child(formatAntDetail(row)).show();

      // Add to the 'open' array
      if ( idx === -1 ) {
        antDetailRows.push( tr.attr('id') );
      }
    }
  } );

  // On each draw, loop over the `detailRows` array and show any child rows
  dt.on( 'draw', function () {
    $.each( detailRows, function ( i, id ) {
      $('#'+id+' td.details-control').trigger( 'click' );
    } );
  } );
});



function formatDataUpdate(d) {
  /*<![CDATA[*/
  var nest = /*[[${nest}]]*/ 'test';
  var dataUpdateList = nest['dataUpdateVisits'];
  /*]]>*/

  var dataUpdate = dataUpdateList[d.index()];
  var antList = dataUpdate['ants'];

  var index;
  var antNames = '';

  for (index in antList) {
    if (antList[index]['name'] !== null) {
      antNames = antNames + antList[index]['name'] + "; ";
    }
  }

  antNames = antNames.trim();
  if (antNames === null || antNames.localeCompare('') === 0) {
    antNames = '-';
  }

  return '<p><strong class="col-md-2 text-right">Ponto Inicial: </strong> Lat ' + dataUpdate['newBeginingPoint']['latitude'] + " Lon " + dataUpdate['newBeginingPoint']['longitude'] + '</p>' +
  '<p><strong class="col-md-2 text-right">Ponto Final: </strong> Lat ' + dataUpdate['newEndingPoint']['latitude'] + " Lon " + dataUpdate['newEndingPoint']['longitude'] + '</p>' +
  '<p><strong class="col-md-2 text-right">Amostras: </strong> ' + antNames + '</p><br/>' +
  '<p><strong class="col-md-2 text-right">Comentários: </strong> ' + dataUpdate['notes'] + '</p><br/>';
}

$(document).ready(function() {
  var dt = $('#data-updates-table').DataTable( {
    responsive: true,
    "language": {
      "url": "/theme/vendor/datatables/lang/portuguese-brasil.lang"
    }
  } );

  // Array to track the ids of the details displayed rows
  var detailUpdateRows = [];

  $('#data-updates-table tbody').on( 'click', 'tr td.details-control', function () {
    var tr = $(this).closest('tr');
    var row = dt.row( tr );
    var idx = $.inArray( tr.attr('id'), detailUpdateRows );

    if ( row.child.isShown() ) {
      tr.removeClass( 'details' );
      row.child.hide();

      // Remove from the 'open' array
      detailUpdateRows.splice( idx, 1 );
    }
    else {
      tr.addClass( 'details' );
      row.child( formatDataUpdate( row ) ).show();

      // Add to the 'open' array
      if ( idx === -1 ) {
        detailUpdateRows.push( tr.attr('id') );
      }
    }
  } );

  // On each draw, loop over the `detailRows` array and show any child rows
  dt.on( 'draw', function () {
    $.each( detailUpdateRows, function ( i, id ) {
      $('#'+id+' td.details-control').trigger( 'click' );
    } );
  } );


} );
