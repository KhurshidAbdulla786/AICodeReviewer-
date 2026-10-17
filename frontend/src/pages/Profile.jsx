import React from 'react';
import Layout from '../components/Layout';
import { useAuth } from '../contexts/AuthContext';
import { User, Mail, Shield, Award, Calendar } from 'lucide-react';

export default function Profile() {
  const { user } = useAuth();

  return (
    <Layout>
      <div className="max-w-4xl mx-auto space-y-6">
        <div>
          <h1 className="text-2xl font-bold text-white">User Profile</h1>
          <p className="text-sm text-gray-400">Account details and review statistics</p>
        </div>

        <div className="bg-gray-900 border border-gray-800 rounded-2xl p-8 shadow-xl space-y-6">
          <div className="flex items-center gap-6 pb-6 border-b border-gray-800">
            <div className="w-20 h-20 rounded-2xl bg-primary-600 text-white flex items-center justify-center text-3xl font-bold shadow-lg shadow-primary-600/30">
              {user?.name ? user.name.charAt(0).toUpperCase() : 'U'}
            </div>
            <div>
              <h2 className="text-xl font-bold text-white">{user?.name || 'Developer'}</h2>
              <p className="text-sm text-gray-400 flex items-center gap-1.5 mt-1">
                <Mail className="w-4 h-4 text-primary-400" /> {user?.email || 'developer@example.com'}
              </p>
              <div className="flex items-center gap-2 mt-3">
                <span className="inline-flex items-center gap-1 px-3 py-1 rounded-full text-xs font-medium bg-primary-500/10 text-primary-400 border border-primary-500/20">
                  <Shield className="w-3.5 h-3.5" /> PRO Plan
                </span>
                <span className="inline-flex items-center gap-1 px-3 py-1 rounded-full text-xs font-medium bg-emerald-500/10 text-emerald-400 border border-emerald-500/20">
                  <Award className="w-3.5 h-3.5" /> Verified Developer
                </span>
              </div>
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6 pt-2">
            <div className="bg-gray-850 border border-gray-800 rounded-xl p-5 text-center">
              <p className="text-2xl font-bold text-primary-400">14</p>
              <p className="text-xs text-gray-400 mt-1">Total Code Reviews</p>
            </div>
            <div className="bg-gray-850 border border-gray-800 rounded-xl p-5 text-center">
              <p className="text-2xl font-bold text-emerald-400">88%</p>
              <p className="text-xs text-gray-400 mt-1">Avg Quality Score</p>
            </div>
            <div className="bg-gray-850 border border-gray-800 rounded-xl p-5 text-center">
              <p className="text-2xl font-bold text-accent-400">32</p>
              <p className="text-xs text-gray-400 mt-1">Bugs Fixed</p>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
}
