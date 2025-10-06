# Dispatcher Configuration Files

Adobe Managed Services handles dispatcher configuration files using a specialized framework that enables AMS to apply best practices without conflicting with custom configurations.

Additional information can be found in the online documentation: 
https://helpx.adobe.com/experience-manager/cloud-manager/using/dispatcher-configurations.html

## File Structure

```
/etc/httpd/
├── conf
│   ├── httpd.conf
│   └── magic
├── conf.d
│   ├── autoindex.conf
│   ├── available_vhosts
│   │   ├── 000_unhealthy_author.vhost
│   │   ├── 000_unhealthy_publish.vhost
│   │   ├── aem_author.vhost
│   │   ├── aem_flush.vhost
│   │   ├── aem_health.vhost
│   │   ├── aem_lc.vhost
│   │   └── aem_publish.vhost
│   ├── dispatcher_vhost.conf
│   ├── enabled_vhosts
│   │   ├── aem_author.vhost -> ../available_vhosts/aem_author.vhost
│   │   ├── aem_flush.vhost -> ../available_vhosts/aem_flush.vhost
│   │   ├── aem_health.vhost -> ../available_vhosts/aem_health.vhost
│   │   └── aem_publish.vhost -> ../available_vhosts/aem_publish.vhost
│   ├── README
│   ├── rewrites
│   │   ├── base_rewrite.rules
│   │   └── xforwarded_forcessl_rewrite.rules
│   ├── userdir.conf
│   ├── variables
│   │   └── ams_default.vars
│   ├── welcome.conf
│   └── whitelists
│       └── 000_base_whitelist.rules
├── conf.dispatcher.d
│   ├── available_farms
│   │   ├── 000_ams_author_farm.any
│   │   ├── 001_ams_lc_farm.any
│   │   └── 999_ams_publish_farm.any
│   ├── cache
│   │   ├── ams_author_cache.any
│   │   ├── ams_author_invalidate_allowed.any
│   │   ├── ams_publish_cache.any
│   │   └── ams_publish_invalidate_allowed.any
│   ├── clientheaders
│   │   ├── ams_author_clientheaders.any
│   │   ├── ams_common_clientheaders.any
│   │   ├── ams_lc_clientheaders.any
│   │   └── ams_publish_clientheaders.any
│   ├── dispatcher.any
│   ├── enabled_farms
│   │   ├── 000_ams_author_farm.any -> ../available_farms/000_ams_author_farm.any
│   │   └── 999_ams_publish_farm.any -> ../available_farms/999_ams_publish_farm.any
│   ├── filters
│   │   ├── ams_author_filters.any
│   │   ├── ams_lc_filters.any
│   │   └── ams_publish_filters.any
│   ├── renders
│   │   ├── ams_author_renders.any
│   │   ├── ams_lc_renders.any
│   │   └── ams_publish_renders.any
│   └── vhosts
│       ├── ams_author_vhosts.any
│       ├── ams_lc_vhosts.any
│       └── ams_publish_vhosts.any
├── conf.modules.d
│   ├── 00-base.conf
│   ├── 00-dav.conf
│   ├── 00-lua.conf
│   ├── 00-mpm.conf
│   ├── 00-proxy.conf
│   ├── 00-systemd.conf
│   ├── 01-cgi.conf
│   └── 02-dispatcher.conf
├── logs -> ../../var/log/httpd
├── modules -> ../../usr/lib64/httpd/modules
└── run -> /run/httpd
```

## Files Explained

- `/etc/httpd/conf.d/<CUSTOMER_CHOICE>.conf`
  - `*.conf` files are Apache specific includes are included from inside the `conf/httpd.conf` file. Making new files or changes to `conf.d/*.conf` files are considered global and may override the defaults in `httpd.conf`. It's best practice to never make changes to `httpd.conf` file but instead use `conf.d/*.conf` files.

- `/etc/httpd/conf.d/rewrites/<CUSTOMER_CHOICE>_rewite.rules`
  - `*_rewrite.rules` files store mod_rewrite rules to be included and consumed explicitly by a Virtual Host file (see below).

- `/etc/httpd/conf.d/available_vhosts/<CUSTOMER_CHOICE>.vhost`
  - `*.vhost` (Virtual Host) files are included from inside the `dispatcher_vhost.conf`. These are `<VirtualHosts>` entries to match host names and allow Apache to handle each domain traffic with different rules. From the `*.vhost` file, other files like rewrites, white listing, etc. will be included. The `available_vhosts` directory is where the `*.vhost` files are stored and `enabled_vhosts` directory is where you enable Virtual Hosts by using a symbolic link from a file in the `available_vhosts` to the `enabled_vhosts` directory.

- `/etc/httpd/conf.d/<CUSTOMER_CHOICE>_ipwhitelist.rules`
  - `*_ipwhitelist.rules` files are included from inside the `*.vhost` files. It contains IP regex or allow deny rules to allow IP white listing. If you're trying to restrict viewing of a virtual host based on IP addresses you'll generate one of these files and include it from your `*.vhost` file.

- `/etc/httpd/conf.dispatcher.d/<CUSTOMER_CHOICE>.any`
  - `mod_dispatcher` sources its settings from `*.any` files. The default parent include file is `conf.dispatcher.d/dispatcher.any`.

- `/etc/httpd/conf.dispatcher.d/available_farms/<author,publish,livecycle or CUSTOMER_CHOICE>_farm.any`
  - `*_farm.any` files are included inside the `conf.dispatcher.d/dispatcher.any` file. These parent farm files exist to control module behavior for each render or website type. Files are created in the `available_farms` directory and enabled with a symbolic link into the `enabled_farms` directory.  The file names begin with `000_` for author and `999_`for publish. Customer's and other farms will fall in-between those to assure the proper include behavior.

- `/etc/httpd/conf.dispatcher.d/filters/<author,publish,livecycle or CUSTOMER_CHOICE>_filters.any`
  - `*_filters.any` files are included from inside the `conf.dispatcher.d/enabled_farms/*_farm.any` files. Each farm has a set of rules change what traffic should be filtered out and not make it to the renderers.

- `/etc/httpd/conf.dispatcher.d/vhosts/<author,publish,livecycle or CUSTOMER_CHOICE>_vhosts.any`
  - `*_vhosts.any` files are included from inside the `conf.dispatcher.d/enabled_farms/*_farm.any` files. These files are a list of host names or URI paths to be matched by blob matching to determine which renderer to use to serve that request.

- `/etc/httpd/conf.dispatcher.d/cache/<author,publish, or CUSTOMER_CHOICE>_cache.any`
  - `*_cache.any` files are included from inside the `conf.dispatcher.d/enabled_farms/*_farm.any`files. These files specify caching preferences.

- `/etc/httpd/conf.dispatcher.d/cache/<author,publish, or CUSTOMER_CHOICE>_invalidate_allowed.any`
  - `*_invalidate_allowed.any` files are included inside the `conf.dispatcher.d/enabled_farms/*_farm.any` files. They specify which IP addresses are allowed to send flush and invalidation requests.

- `/etc/httpd/conf.dispatcher.d/clientheaders/<author,publish,livecycle, or CUSTOMER_CHOICE>_clientheaders.any`
  - `*_clientheaders.any` files are included inside the `conf.dispatcher.d/enabled_farms/*_farm.any` files. They specify which client headers should be passed through to each renderer.

- `/etc/httpd/conf.dispatcher.d/renders/<author,publish,livecycle, or CUSTOMER_CHOICE>_renders.any`
  - `*_renders.any` files are included inside the `conf.dispatcher.d/enabled_farms/*_farm.any` files. They specify IP, port, and timeout settings for each renderer. A proper renderer can be a LiveCycle server or any AEM systems where the dispatcher can fetch / proxy the requests from

## Environment Variables for node-specific Elements

Apache files and dispatcher (.any) files can consume environment variables.  Cloud Manager requires the same files to be deployed to all dispatchers so variables must be used to handle distinctions between dispatcher nodes (i.e. render IPs)

- `.any` example: `/etc/httpd/conf/publish-renders.any`

```
 /0
   {
   /hostname "${PUBLISH_IP}"
   /port "4503"
   /timeout "10000"
   }
```

- `.vhost` example: `/etc/httpd/conf.d/enabled_vhosts/aem_author.vhost`

```
 PassEnv DISP_ID
 <VirtualHost \*:80>
	ServerName	"author-exampleco-dev.adobecqms.net"
   <IfModule mod_headers.c>
        Header add X-Dispatcher ${DISP_ID}
    </IfModule>
```

Environment variables can be placed in two locations:

1. `/etc/sysconfig/httpd`
  - AMS maintains this file and will handle initial configuration on each dispatcher node
2. `/etc/httpd/conf.d/variables/ams_default.vars`
  - Customer maintains this file and includes it in source control

## Immutable Configuration Files

Some files are immutable, meaning they cannot be altered or deleted.  These are part of the base framework and enforce standards and best practices.  When customization is needed, copies of immutable files (i.e. `aem_publish.vhost` -> `weretail_publish.vhost`) can be used to modify the behavior.  Where possible, be sure to retain includes of immutable files unless customization of included files is also needed.

### Immutable Files

```
conf.dispatcher.d/available_farms/999_ams_publish_farm.any
conf.dispatcher.d/available_farms/000_ams_author_farm.any
conf.dispatcher.d/available_farms/001_ams_lc_farm.any
conf.dispatcher.d/filters/ams_publish_filters.any
conf.dispatcher.d/filters/ams_author_filters.any
conf.dispatcher.d/filters/ams_lc_filters.any
conf.dispatcher.d/renders/ams_author_renders.any
conf.dispatcher.d/renders/ams_publish_renders.any
conf.dispatcher.d/renders/ams_lc_renders.any
conf.dispatcher.d/cache/ams_author_invalidate_allowed.any
conf.dispatcher.d/cache/ams_author_cache.any
conf.dispatcher.d/cache/ams_publish_invalidate_allowed.any
conf.dispatcher.d/cache/ams_publish_cache.any
conf.dispatcher.d/clientheaders/ams_publish_clientheaders.any
conf.dispatcher.d/clientheaders/ams_author_clientheaders.any
conf.dispatcher.d/clientheaders/ams_lc_clientheaders.any
conf.dispatcher.d/clientheaders/ams_common_clientheaders.any
conf.dispatcher.d/dispatcher.any
conf.dispatcher.d/vhosts/ams_publish_vhosts.any
conf.dispatcher.d/vhosts/ams_author_vhosts.any
conf.dispatcher.d/vhosts/ams_lc_vhosts.any
conf.dispatcher.d/available_farms/000_ams_author_farm.any
conf.dispatcher.d/available_farms/001_ams_lc_farm.any
conf.dispatcher.d/available_farms/999_ams_publish_farm.any
conf.d/rewrites/xforwarded_forcessl_rewrite.rules
conf.d/rewrites/base_rewrite.rules
conf.d/whitelists/000_base_whitelist.rules
conf.d/dispatcher_vhost.conf
conf.d/available_vhosts/aem_flush.vhost
conf.d/available_vhosts/aem_author.vhost
conf.d/available_vhosts/aem_publish.vhost
conf.d/available_vhosts/aem_lc.vhost
conf.d/available_vhosts/000_unhealthy_author.vhost
conf.d/available_vhosts/000_unhealthy_publish.vhost
conf.modules.d/00-base.conf
conf.modules.d/00-dav.conf
conf.modules.d/00-lua.conf
conf.modules.d/00-mpm.conf
conf.modules.d/00-mpm.conf.old
conf.modules.d/00-proxy.conf
conf.modules.d/00-systemd.conf
conf.modules.d/01-cgi.conf
conf.modules.d/02-dispatcher.conf
conf/httpd.conf
```
