// Sortable Table library, by https://www.kryogenix.org/code/browser/sorttable/

var port = '8080';
var qs = require('querystring');
var http = require('http');
var htmlBody = function(tableContent) {
    var htmlStart =  '<!DOCTYPE html>';
        htmlStart += '<html lang="en">';
        htmlStart += '<head>';
        htmlStart += '<meta charset="UTF-8">';
        htmlStart += '<title>BookSurf</title>';
		htmlStart += '<style>';
		htmlStart += 'body { font-family: verdana; font-size: 18px; color: white; background: url("http://whytoread.com/wp-content/uploads/2015/03/10-Best-Existential-Fiction-Books-That-Will-Alter-Your-Existence.jpg"); backgroup-size: cover; margin-top: 0px; margin-bottom: 0px; margin-left: 0px; margin-right: 0px; }\n';
		htmlStart += 'table { opacity: .85; background-color: rgb(000,000,000); width: 800px; font-family: verdana; font-size: 12px; }';
		htmlStart += 'a { color: white; }';
		htmlStart += 'input[type="text"] { width: 715px; }';
		htmlStart += 'input[type="submit"] { width: 80px; }';
		htmlStart += '</style>';
        htmlStart += '</head>';
		htmlStart += '<script src="sorttable.js"></script>'
        htmlStart += '<body>';
		htmlStart += '<br/>BookSurf: The On-Line Bookstore Shopping Aggregator<br/><br/>';
        htmlStart += '<form method="POST" id="formSearch">';
        htmlStart += '<input align="center" type="text" id="q" name="q" placeholder="Title, author, ISBN"/>';
        htmlStart += '<input align="center" type="submit" id="seachBtn" value="Search"/>';
        htmlStart += '</form>';
		htmlStart += '<table>';
        htmlStart += tableContent;
		htmlStart += '</table>';
        htmlStart += '</body>';
        htmlStart += '</html>';
    return htmlStart;
}

// query string -> f(x) -> CSV text of search results
function callText(input) {
    var outVal = '';
    var jsonOut = '';
	var booksurfJar = '/media/ehd/booksurf/target/booksurf-1.0.jar';
	var booksurfLib = '/media/ehd/booksurf/target/lib/jsoup-1.10.2.jar';
    var exec = require('child_process');
    exec.execSync('echo \"url,title,price\" > temp.csv');
	exec.execSync('java -cp "' + booksurfLib + '" -jar "' + booksurfJar + '" "' + input + '" >> temp.csv');
    exec.execSync('mongo booksurf --eval \"db.dropDatabase()\"');
	exec.execSync('mongoimport -d booksurf --type csv --file temp.csv --headerline');
	outVal += exec.execSync('./dbWrite');
    return outVal;
}

// starts server instance
var server = http.createServer(function (req, res) {
    if (req.method === 'GET') {
        res.writeHead(200, {'Content-Type': 'text/html'});
        res.end(htmlBody(''));
    } else 
    if (req.method === 'POST') {
        var body = '';
        req.on('data', function(data) { body += data; });
    }
    req.on('end', function() {
        var post = qs.parse(body);
        if (post.q != undefined) {
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(htmlBody(callText(post.q)));
        }
    });
});

server.listen(port);
console.log('Server running at localhost:' + port);
