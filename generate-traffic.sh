export IP=`kubectl get service elastic-book-service-lb -o=jsonpath='{.status.loadBalancer.ingress[0].ip}{"\n"}'`

echo "Using IP as $IP"
echo ""

http http://$IP/api/book/
http http://$IP/api/book/1
http http://$IP/api/book/2 
http http://$IP/api/book/3
http http://$IP/api/book/4

