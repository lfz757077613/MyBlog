#!/bin/sh

usage ()
{
    echo "Usage: $0 {start|stop|online|offline|restart}"
    exit 1
}

export MYHOME=${HOME}
export APPNAME=${project.build.finalName}
export STATUS_PORT=8096
export APP_HOME=${MYHOME}/${APPNAME}
export JAVA=/opt/taobao/java/bin/java
export LOGPATH=${MYHOME}/logs

mkdir -p ${MYHOME}/logs

start ()
{
    SERVICE_OPTS="-server -jar"
    SERVICE_OPTS="${SERVICE_OPTS} -Xms8g -Xmx8g"
    SERVICE_OPTS="${SERVICE_OPTS} -Xmn4g"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:MaxDirectMemorySize=2g"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:SurvivorRatio=10"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+UseConcMarkSweepGC -XX:CMSMaxAbortablePrecleanTime=5000"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+CMSClassUnloadingEnabled -XX:CMSInitiatingOccupancyFraction=80 -XX:+UseCMSInitiatingOccupancyOnly"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+ExplicitGCInvokesConcurrent -Dsun.rmi.dgc.server.gcInterval=2592000000 -Dsun.rmi.dgc.client.gcInterval=2592000000"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:ParallelGCThreads=24"
    SERVICE_OPTS="${SERVICE_OPTS} -Xloggc:${LOGPATH}/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps"
    SERVICE_OPTS="${SERVICE_OPTS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOGPATH}/java.hprof"
    SERVICE_OPTS="${SERVICE_OPTS} -Dsun.net.client.defaultConnectTimeout=10000"
    SERVICE_OPTS="${SERVICE_OPTS} -Dsun.net.client.defaultReadTimeout=30000"
    SERVICE_OPTS="${SERVICE_OPTS} -Djava.awt.headless=true"
    online
    nohup ${JAVA} ${SERVICE_OPTS} ${APP_HOME}/${APPNAME}.jar > ${LOGPATH}/nohup_stdout.log 2>&1 &
    for e in $(seq 90); do
        echo "check http://127.0.0.1:${STATUS_PORT}/status.taobao ${e}"
        sleep 1
        status_code=`/usr/bin/curl -L -o /dev/null --connect-timeout 1 -s -w %{http_code}  "http://127.0.0.1:${STATUS_PORT}/status.taobao"`
        if [ ${status_code} == 200 ];then
            echo "check http://127.0.0.1:${STATUS_PORT}/status.taobao success"
            return 0
        fi
    done
    echo "check http://127.0.0.1:${STATUS_PORT}/status.taobao fail"
    return 1 # status.taobao check failed
}

stop()
{
    offline
    for e in $(seq 20); do
        echo "wait offline ${e} ..."
        sleep 1
    done

    STR=`ps -ef | grep java | grep "$APPNAME"`
    [ -z "$STR" ] && return 0
    kill `ps -ef | grep java | grep "$APPNAME" | awk '{print $2}'`
    while true
    do
        STR=`ps -ef | grep java | grep "$APPNAME"`
        [ -z "$STR" ] && break || sleep 1
        echo "wait kill ..."
    done
}

online()
{
    touch -m $APP_HOME/status.taobao
}

offline()
{
    rm -f $APP_HOME/status.taobao
}

case $1 in

    start)
        start
    ;;

    stop)
        stop
    ;;

    restart)
        stop
        start
    ;;

    online)
        online
    ;;

    offline)
        offline
    ;;

    *)
        usage
    ;;

esac
