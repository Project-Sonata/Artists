## Sonata artists


Create artist
Artist is the same user but with ability to upload tracks, check statistic and get analytics.

Scopes for artist:
upload-track public - used to upload the track to Sonata server // in priority
analytics - read analytics about arist // not in priority
statistic get statistic(number of followers, number of new followers in this month, most popular track. etc) // not in priority

Artist must have account, the data is shared using cookies because of subdomain
Frontend has token from cookies, send this and if there is no artist role then artist must be created.

If user become an artist event publishes to kafka and update role

Check token, get id if there is artist in DB send OK and do nothing.

If user isn't an artist then create form with user account(WANNACRY for example)
set artist background, artist pick, artist albums

POST /become-artist

status: "ALREADY_ARTIST"

status: "STARTING"
account: {
    "name": "Odeyalo"
}