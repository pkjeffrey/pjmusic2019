-- :name get-artist :? :1
-- :doc get artist by id
select id, name
from artist
where id = :id

-- :name get-artists :? :*
-- :doc get list of all artists
select id, name
from artist

-- :name get-artist-releases :? :*
-- :doc get releases for artist
select id, title, released, label, catalog
from release
where artist = :id
order by released, added

-- :name get-artist-appears-on :? :*
-- :doc get releases that artist appears on
select distinct r.id, r.title, r.released, r.label, r.catalog
from release r
inner join media m
on m.release = r.id
inner join track t
on t.media = m.id
where t.artist = :id
and t.artist <> r.artist
order by r.released, r.title

-- :name get-release :? :1
-- :doc get release by id
select id, artist, title, released, label, catalog, compilation
from release
where id = :id

-- :name get-release-art :? :1
-- :doc get release artwork by release id
select art
from release
where id = :id

-- :name get-release-media-descrs :? :*
-- :doc get media descriptions for release
select count(*) cnt, f.name
from format f
inner join media m
on m.format = f.id
where m.release = :id
group by f.name, f.sort
order by f.sort

-- :name get-release-medias :? :*
-- :doc get medias for release
select m.id, f.name, m.title
from media m
inner join format f
on f.id = m.format
where release = :id
order by m.id

-- :name get-media-tracks :? :*
-- :doc get tracks for media
select t.id, t.side, t.number, t.title, t.artist, a.name artistname
from track t
inner join artist a
on a.id = t.artist
where t.media = :id
order by t.side, t.number

-- :name get-recent :? :*
-- :doc get recent additions
select id, artist, title, released, label, catalog
from release
order by added desc
fetch first :num rows only
