Xatkit Spotify Platform
=====

[![License Badge](https://img.shields.io/badge/license-EPL%202.0-brightgreen.svg)](https://opensource.org/licenses/EPL-2.0)
[![Build Status](https://travis-ci.com/xatkit-bot-platform/xatkit-spotify-platform.svg?branch=master)](https://travis-ci.com/xatkit-bot-platform/xatkit-spotify-platform)
[![Wiki Badge](https://img.shields.io/badge/doc-wiki-blue)](https://github.com/xatkit-bot-platform/xatkit/wiki/Xatkit-Spotify-Platform)

Integrate GIFs from [Spotify](https://spotify.com/) in your execution model.


## Providers

The Spotify platform does not define any provider.

## Actions

| Action | Parameters                                                   | Return                         | Return Type | Description                                                 |
| ------ | ------------------------------------------------------------ | ------------------------------ | ----------- | ----------------------------------------------------------- |
| GetGif | - `search`(**String**): the search query used to retrieve GIFs (can be a single word or a sentence) | The `url` of the retrieved GIF | String      | Returns the first GIF matching the provided `search` query. |

## Options

The Spotify platform supports the following configuration options

| Key                  | Values | Description                                                  | Constraint    |
| -------------------- | ------ | ------------------------------------------------------------ | ------------- |
| `xatkit.spotify.token` | String | The [developer](https://developers.spotify.com/) token to use to query the Spotify API | **Mandatory** |

