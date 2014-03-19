# Script for daily backups of lagrummet db to be run as cron job
# The result of mysqldump is copied to folder on ci.lagrummet.se

BAKFILE=lagrummet_$(date +"%Y%m%d_%H%M").sql
mysqldump lagrummet > ~/mysql_backup/$BAKFILE
scp ~/mysql_backup/$BAKFILE rinfo@ci.lagrummet.se:/home/rinfo/beta_lagrummet_mysql_backups/