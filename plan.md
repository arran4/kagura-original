1. Address the security issue by adding Subresource Integrity (SRI) hashes and `crossorigin="anonymous"` to all CDN `<script>` and `<link>` tags in both `head.tag` and `footer.tag`.
We will use bash to download the files and calculate their sha256 checksum, then add it.
Or we can use an online srihash site via cURL, but calculating it locally using `openssl dgst -sha256 -binary | openssl base64 -A` is safer.

For example:
`curl -s https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css | openssl dgst -sha384 -binary | openssl base64 -A`

2. Remove `spin.min.js` from `head.tag`.

Let's first calculate hashes:
- `https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap.min.css`
- `https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/css/bootstrap-responsive.min.css`
- `https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/2.0.1/css/bootstrap-datetimepicker.min.css`
- `https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js`
- `https://cdnjs.cloudflare.com/ajax/libs/spin.js/1.3.3/spin.min.js`
- `https://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js`
- `https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js`
- `https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/2.0.1/js/bootstrap-datetimepicker.min.js`
