import { useState, useEffect } from 'react';
import Layout from '../components/Layout';
import apiClient from '../api/axios';
import { History, ExternalLink, Star, Clock, AlertTriangle, CheckCircle } from 'lucide-react';
import { Link } from 'react-router-dom';

export default function ReviewHistory() {
  const [reviews, setReviews] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchReviews();
  }, []);

  const fetchReviews = async () => {
    try {
      const res = await apiClient.get('/reviews?size=50');
      setReviews(res.data.data || []);
    } catch (err) {
      console.error('Failed to load reviews:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout>
      <div className="space-y-6">
        <div>
          <h1 className="text-2xl font-bold text-white">Review History</h1>
          <p className="text-gray-400 mt-1">All your past code reviews</p>
        </div>

        {loading ? (
          <div className="text-center py-12">
            <div className="animate-spin rounded-full h-8 w-8 border-t-2 border-b-2 border-primary-500 mx-auto" />
          </div>
        ) : reviews.length === 0 ? (
          <div className="card text-center py-12">
            <History className="w-12 h-12 text-gray-600 mx-auto mb-3" />
            <p className="text-gray-500">No review history yet.</p>
            <Link to="/editor" className="text-primary-400 hover:text-primary-300 text-sm mt-2 inline-block">
              Start reviewing code
            </Link>
          </div>
        ) : (
          <div className="space-y-3">
            {reviews.map((review) => (
              <div key={review.id} className="card-hover flex items-center justify-between">
                <div className="flex items-center gap-4">
                  <div className={`p-2.5 rounded-lg ${
                    review.overallScore >= 80 ? 'bg-green-500/10' :
                    review.overallScore >= 60 ? 'bg-yellow-500/10' : 'bg-gray-800'
                  }`}>
                    {review.overallScore != null ? (
                      <Star className={`w-5 h-5 ${
                        review.overallScore >= 80 ? 'text-green-400' :
                        review.overallScore >= 60 ? 'text-yellow-400' : 'text-gray-500'
                      }`} />
                    ) : (
                      <Clock className="w-5 h-5 text-gray-500" />
                    )}
                  </div>
                  <div>
                    <p className="text-white font-medium">{review.filename || 'Unknown file'}</p>
                    <div className="flex items-center gap-3 text-xs text-gray-500 mt-1">
                      <span>{new Date(review.createdAt).toLocaleDateString()}</span>
                      <span>{review.status}</span>
                      {review.aiModelUsed && <span>Model: {review.aiModelUsed}</span>}
                    </div>
                  </div>
                </div>
                <div className="flex items-center gap-4">
                  {review.overallScore != null && (
                    <span className={`text-lg font-bold ${
                      review.overallScore >= 80 ? 'text-green-400' :
                      review.overallScore >= 60 ? 'text-yellow-400' : 'text-red-400'
                    }`}>
                      {review.overallScore}%
                    </span>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </Layout>
  );
}