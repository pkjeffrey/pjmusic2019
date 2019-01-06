-- :name get-artist :? :1
-- :doc get artist by id
select id, name
from artist
where id = :id

-- :name get-artist-releases :? :*
-- :doc get releases for artist
select id, title, released, label, catalog
from release
where artist = :id
order by released, added

-- :name get-release :? :1
-- :doc get release by id
select id, artist, title, released, label, catalog
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
select id, side, number, title
from track
where media = :id
order by side, number

-- :name get-recent :? :*
-- :doc get recent additions
select id, artist, title, released, label, catalog
from release
order by added desc
fetch first :num rows only
